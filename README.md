# Datomic demo

The goal of this repo is to give you a small glimpse of Datomic capabilities.

## Set-up

First download the Datomic Free distribution from [here](http://www.datomic.com/get-datomic.html) and unzip it.  
Then start the transactor:

```
bin/transactor config/samples/free-transactor-template.properties
```

You will also need to install [boot](https://github.com/boot-clj/boot) to start a REPL session with the examples.  
Run the following command in the main demo directory.

```
boot repl
```

## Initiate the database

Before you can run the examples, you will need to create a new database and define a schema.
This can be done from the repl using the following commands:

```clj
;; Define the database URI
(def uri "datomic:free://localhost:4334/demo")

;; Import Datomic bindings
(require '[datomic.api :as d])

;; Create a new database at the given URI
(d/create-database uri)

;; Connect to the created database
(def conn (d/connect uri))

;; Import setup functions
(require '[demo.setup :as s])

;; Define a database schema
(s/init-schema conn)

;; Populate the database with some initial entries
(s/init-db conn)
```

The initial schema and contents of the database are defined in `setup.clj`.

## Running the examples

Once you've set up the database, you can proceed to running queries and transactions.  
First, import the examples and display a list of all people in the database:

```clj
;; Import examples
(require '[demo.examples :as e])

;; List all people currently in the database
(e/list-people (d/db conn))
```

You should see a result set that looks more or less like this:

```clj
#{[17592186045423 "Inez" "Christensen"] [17592186045420 "Spencer" "Hall"] [1759218604421 "Kevin" "Wolfe"] [17592186045418 "Tyrone" "Norman"] [17592186045424 "Meghan" "Fletcher"]}
```

You can use the functions from `datomic.api` namespace to run any transactions and queries other than the examples listed below right from the REPL.

### Examples overview:

- **list-people**: Shows an overview of all the people in the database.

```clj
(list-people (d/db conn))
```

- **add-person**: Adds a new person to the database.

```clj
(add-person conn "John" "Smith" 1992)
```

- **list-generation**: Lists all the people from the same generation. Available generations are: *J, X, Y, Z*.

```clj
(list-generation (d/db conn) "X")
```

- **list-companies**: Lists all the companies in the database.

```clj
(list-companies (d/db conn))
```

- **add-employee**: Adds a person to company's employees.

```clj
(add-employee conn "Apple Inc." 17592186045418)
```

- **move-employee**: Transitions an employee from one company to another

```clj
(move-employee conn 17592186045419 17592186045422 17592186045418)
```

- **list-jobs**: Lists all companies a person ever worked at.

```clj
(list-jobs (d/db conn) "John" "Smith")
```

The last example aims to show how we can utilize transactions and Datomic's concept of time to query data we did not account for in the original schema.

## Useful resources

Other useful resources for learning about Datomic:

- [www.learndatalogtoday.org](http://www.learndatalogtoday.org)
- [docs.datomic.com](http://docs.datomic.com)
- [github.com/Datomic/mbrainz-sample](https://github.com/Datomic/mbrainz-sample)
