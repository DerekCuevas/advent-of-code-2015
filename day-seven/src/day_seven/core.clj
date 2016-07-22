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
    (if (number? token)
      token
      (keyword token))))

(defn- parse-lhs
  ([input]
    input)
  ([gate input]
    {:inputs [input] :gate (gate->fn gate)})
  ([input-a gate input-b]
    {:inputs [input-a input-b] :gate (gate->fn gate)}))

(defn- parse-instruction [s]
  (let [[lhs rhs] (split s #" -> ")]
    {(keyword rhs)
     (->> (split lhs #" ")
          (map parse-token)
          (apply parse-lhs))}))

(defn build-circuit [instructions]
  (->> (map parse-instruction instructions)
       (reduce merge {})))

(def probe
  (memoize
   (fn [circuit wire]
     (let [input (get circuit wire)]
       (cond
         (number? input) input
         (keyword? input) (probe circuit input)
         :else (->> (:inputs input)
                    (map #(if (keyword? %) (probe circuit %) %))
                    (apply (:gate input))))))))
