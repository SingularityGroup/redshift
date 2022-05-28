(ns
    org.sg.redshift.results
    (:require
     [clojure.string :as str]
     [clojure.java.io :as io]))

(defn get-val [[k v]]
  (case k :isNull nil v))

(defn sanitize [s]
  (str/replace s #"\s+" "-"))

(comment
 (sanitize "fo bar"))

(def ->vals-xf (comp cat (map get-val)))

(defn
  ->results
  [{:keys [Records ColumnMetadata]}]
  (let [r-keys (into
                []
                (map
                 (comp keyword sanitize :name))
                ColumnMetadata)]
    (sequence
     (map
      (fn
        [vals2]
        (zipmap
         r-keys
         (sequence
          ->vals-xf
          vals2))))
     Records)))
