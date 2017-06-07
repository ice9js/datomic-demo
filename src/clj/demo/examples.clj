(ns demo.examples
  (:require [datomic.api :as d]))

(def uri "datomic:free://localhost:4334/demo")
(def conn (d/connect uri))
(def db (d/db conn))

(defn list-people [db]
  (d/q '[:find ?name ?surname
         :in $
         :where
         [?p :person/name ?name]
         [?p :person/surname ?surname]]
       db))

(defn add-person [conn name surname birth-year]
  (d/transact conn [{:person/name name
                     :person/surname surname
                     :person/birth-year birth-year}]))

(defn generation [birth-year]
  (if (< birth-year 1966)
      "J"
      (if (< birth-year 1977)
          "X"
          (if (< birth-year 1995) "Y" "Z"))))

(defn list-generation [db generation]
  (d/q '[:find ?name ?surname
         :in $ ?generation
         :where
         [?p :person/name ?name]
         [?p :person/surname ?surname]
         [?p :person/birth-year ?born]
         [(demo.examples/generation ?born) ?generation]]
       db
       generation))

(defn list-companies [db company]
  (d/q '[:find ?company
         :in $
         :where
         [_ :company/name ?company]]
       db))

(defn list-employees [db company]
  (d/q '[:find ?name ?surname
         :in $ ?company
         :where
         [?c :company/name ?company]
         [?c :company/employee ?p]
         [?p :person/name ?name]
         [?p :person/surname ?surname]]
       db
       company))

(defn add-employee [conn company employee-id]
  (d/transact conn [{:company/name company
                     :company/employees employee-id}]))

(defn move-employee [conn from-company to-company employee-id]
  (d/transact conn [[:db/add :from :company/name from-company]
                    [:db/add :from :company/employees employee-id]
                    [:db/add :to :company/name to-company]
                    [:db/retract :to :company/employees employee-id]]))

(defn list-jobs [db employee-id]
  (d/q '[:find ?company ?start-date
         :in $ ?employee
         :where
         [?c :company/name ?company]
         [?c :company/employees ?employee ?tx]
         [?tx :db/txInstant ?start-date]
         [?tx]]
       (d/history db)
       employee-id))
