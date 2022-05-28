# Concept

Provide an interaction layer with aws redshift-data.

# Usage

```clojure

(defn
  redshift-data
  []
  (aws/client
   {:region "us-east-1"
    :api :redshift-data}))


(def
    db-opts
    {:Database ..
     :ClusterIdentifier ..
     :DbUser ...})

(nom/nom->>
 (org.sg.redshift/query!
  (redshift-data)
  (merge
   {:Sql "..."}
   db-opts))
 (sequence
  (mapcat
   org.sg.redshift.results/->results)))
```
