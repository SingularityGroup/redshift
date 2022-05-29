(ns
    org.sg.redshift
    (:require
     [de.otto.nom.core :as nom]
     [cognitect.anomalies
      :as-alias
      anom]
     [org.sg.aws.client :as aws]
     [clojure.tools.logging :as log]))

(defn query [req]
  {:op :ExecuteStatement :request req})

(defn
  result-iteration
  [client query]
  (iteration
   (fn
    [nextToken]
     (aws/invoke
      client
      {:op :GetStatementResult
       :request (assoc
                 query
                 :NextToken
                 nextToken)}))
   {:kf :NextToken}))

(defn
  get-result-blocking
  "Returns a result [[clojure.core/iteration]], or an anomaly."
  [client req]
  (loop
      []
      (let [res (do
                  (Thread/sleep 400)
                  (aws/invoke
                   client
                   {:op :DescribeStatement
                    :request req}))]
        (log/info (prn-str req))
        (condp
            some
            [(:Status res)]
            #{"PICKED" "SUBMITTED"
              "STARTED"}
            (recur)
            #{"ABORTED"}
            (assoc
             res
             ::anom/category
             ::anom/interupted)
            #{"FAILED"}
            (assoc
             res
             ::anom/category
             ::anom/fault)
            #{"FINISHED"}
            (result-iteration client req)
            res))))

(defn
  query!
  "Returns a result [[clojure.core/iteration]], or an anomaly."
  [client req]
  (nom/let-nom>
      [req (aws/invoke client (query req))]
      (get-result-blocking
       client
       req)))

(comment)
