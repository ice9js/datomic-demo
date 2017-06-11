(ns demo.setup
  (:require [datomic.api :as d]))

(defn init-schema [conn]
  (d/transact conn [{:db/id #db/id[:db.part/db]
                     :db/ident :person/name
                     :db/valueType :db.type/string
                     :db/cardinality :db.cardinality/one
                     :db/doc "A person's name"
                     :db.install/_attribute :db.part/db}

                    {:db/id #db/id[:db.part/db]
                     :db/ident :person/surname
                     :db/valueType :db.type/string
                     :db/cardinality :db.cardinality/one
                     :db/doc "A person's surname"
                     :db.install/_attribute :db.part/db}

                    {:db/id #db/id[:db.part/db]
                     :db/ident :person/birth-year
                     :db/valueType :db.type/long
                     :db/cardinality :db.cardinality/one
                     :db/unique :db.unique/identity
                     :db/doc "A person's birth year"
                     :db.install/_attribute :db.part/db}

                    {:db/id #db/id[:db.part/db]
                     :db/ident :company/name
                     :db/valueType :db.type/string
                     :db/cardinality :db.cardinality/one
                     :db/unique :db.unique/identity
                     :db/doc "A company name"
                     :db.install/_attribute :db.part/db}

                    {:db/id #db/id[:db.part/db]
                     :db/ident :company/employee
                     :db/valueType :db.type/ref
                     :db/cardinality :db.cardinality/many
                     :db/doc "Company employees"
                     :db.install/_attribute :db.part/db}
                    ]))

(defn init-db [conn]
  (d/transact conn [{:person/name "Tyrone"
                     :person/surname "Norman"
                     :person/birth-year 1984}
                    {:company/name "Apple Inc."
                     :company/employee [{:person/name "Spencer"
                                         :person/surname "Hall"
                                         :person/birth-year 1964}
                                        {:person/name "Kevin"
                                         :person/surname "Wolfe"
                                         :person/birth-year 1980}]}
                    {:company/name "Google Inc."
                     :company/employee [{:person/name "Inez"
                                         :person/surname "Christensen"
                                         :person/birth-year 1975}
                                        {:person/name "Meghan"
                                         :person/surname "Fletcher"
                                         :person/birth-year 1996}]}]))
