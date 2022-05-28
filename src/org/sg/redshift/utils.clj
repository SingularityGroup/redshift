(ns org.sg.redshift.utils)

(defn
  parameters
  [parameters-map]
  (into
   []
   (map
    (fn [[k v]] {:name k :value (str v)}))
   parameters-map))

(comment
  (assert
   (=
    [{:name "limit" :value (str 9)}]
    (parameters {"limit" "9"}))))
