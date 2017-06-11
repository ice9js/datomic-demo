(ns demo.examples
  (:require [datomic.api :as d]))

(defn list-people [db]
  (d/q '[:find ?p ?name ?surname
         :in $
         :where
         [?p :person/name ?name]
         [?p :person/surname ?surname]]
       db))

(defn add-person [conn name surname birth-year]
  (d/transact conn [{:person/name name
                     :persona/surname surname
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

(defn list-companies [db]
  (d/q '[:find ?e ?company
         :in $
         :where
         [?e :company/name ?company]]
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
                     :company/employee employee-id}]))

(defn move-employee [conn from-company to-company employee-id]
  (d/transact conn [[:db/retract from-company :company/employee employee-id]
                    [:db/add to-company :company/employee employee-id]]))

(defn list-jobs [db name surname]
  (d/q '[:find ?company ?start-date
         :in $ ?name ?surname ?added
         :where
         [?e :person/name ?name]
         [?e :person/surname ?surname]
         [?c :company/name ?company]
         [?c :company/employee ?e ?tx ?added]
         [?tx :db/txInstant ?start-date]]
       l
       name
       surname
       true))
