# Concept

Provide an interaction layer with aws redshift-data.

# Usage

add deps

```edn
org.sg.redshift/redshift {:git/url "git@github.com:SingularityGroup/redshift.git" :sha "c5ca9b815545971e32f039d17dbb192b78da19ec"}
```


```clojure
(defn
  redshift-data
  []
  (aws/client
   {:region "us-east-1"
    :api :redshift-data}))

(def
  db-opts
  {:Database ".."
   :ClusterIdentifier ".."
   :DbUser ".."})

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
