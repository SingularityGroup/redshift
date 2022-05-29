(ns
    org.sg.aws.impl.client
    (:require
     #?(:bb [pod.babashka.aws :as aws]
        :clj [cognitect.aws.client.api :as aws])))

(def invoke aws/invoke)
