(ns day-seven.core
  (:require [clojure.string :refer [split]])
  (:gen-class))

(def ^:private input (split (slurp "resources/input.txt") #"\n"))

;; FIXME: double check bit-not !!!
(def ^:private gate->fn
  {:AND bit-and
   :OR bit-or
   :LSHIFT bit-shift-left
   :RSHIFT bit-shift-right
   :NOT bit-not})

(defn- parse-input [s]
  (let [input (read-string s)]
    (if (number? input) input (keyword input))))

(defn- parse-op
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
                (map parse-input)
                (apply parse-op)))))

(defn- add-connection [circuit {:keys [gate input output]}]
  (->> (if gate {:gate gate :input input} input)
       (assoc circuit output)))

(defn- build-circuit [instructions]
  (->> (map parse-instruction instructions)
       (reduce add-connection {})))

(defn- probe [circuit wire]
  (let [value (get circuit wire)]
    (cond
      (number? wire) wire
      (number? value) value
      (keyword? value) (probe circuit value)
      :else (apply (:gate value)
                   (map (partial probe circuit)
                        (:input value))))))
