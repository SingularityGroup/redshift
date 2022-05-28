# Concept

Provide an interaction layer with aws redshift-data.

# Usage

add deps

```edn

        org.sg.redshift/redshift {:git/url "git@github.com:SingularityGroup/redshift.git"
                                    :sha "ca0cdb3a9cb5291cbf7a57758cfcab583a6aeacf"}
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
