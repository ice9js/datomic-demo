(ns demo.setup
  (:require [datomic.api :as d]
            [demo.examples :refer [conn]]))

(defn init-db []
  (d/transact conn [{:db/id :one
                     :person/name "Spencer"
                     :person/surname "Hall"
                     :person/birth-year 1964}

                    {:db/id :two
                     :person/name "Kevin"
                     :person/surname "Wolfe"
                     :person/birth-year 1980}

                    {:db/id :three
                     :person/name "Inez"
                     :person/surname "Christensen"
                     :person/birth-year 1975}

                    {:db/id :four
                     :person/name "Meghan"
                     :person/surname "Fletcher"
                     :person/birth-year 1996}

                    {:db/id :five
                     :person/name "Tyrone"
                     :person/surname "Norman"
                     :person/birth-year 1984}

                    {:company/name "Apple Inc."
                     :company/employee :one
                     :company/employee :two}

                    {:company/name "Google Inc."
                     :company/employee :three
                     :company/employee :four}]))
