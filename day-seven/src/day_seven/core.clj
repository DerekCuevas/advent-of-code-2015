(ns day-seven.core
  (:require [clojure.string :refer [split]])
  (:gen-class))

(def ^:private gate->fn
  {:AND bit-and
   :OR bit-or
   :LSHIFT bit-shift-left
   :RSHIFT bit-shift-right
   :NOT bit-not})

(defn- parse-token [s]
  (let [token (read-string s)]
    (if (number? token) token (keyword token))))

(defn- parse-lhs
  ([input]
    {:input input})
  ([gate input]
    {:input [input] :gate (gate->fn gate)})
  ([input-a gate input-b]
    {:input [input-a input-b] :gate (gate->fn gate)}))

(defn- parse-instruction [s]
  (let [[lhs rhs] (split s #" -> ")]
    (merge {:output (keyword rhs)}
           (->> (split lhs #" ")
                (map parse-token)
                (apply parse-lhs)))))

(defn- add-connection [circuit {:keys [gate input output]}]
  (->> (if gate {:gate gate :input input} input)
       (assoc circuit output)))

(defn build-circuit [instructions]
  (->> (map parse-instruction instructions)
       (reduce add-connection {})))

(def probe
  (memoize
   (fn [circuit wire]
     (let [connection (get circuit wire)]
       (cond
         (number? connection) connection
         (keyword? connection) (probe circuit connection)
         :else (->> (:input connection)
                    (map #(if (keyword? %) (probe circuit %) %))
                    (apply (:gate connection))))))))
