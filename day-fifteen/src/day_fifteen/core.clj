(ns day-fifteen.core
  (:require [clojure.string :refer [split]])
  (:gen-class))

;; temp
(def input (-> "resources/input.txt" slurp (split #"\n")))

(def ingredient-format
  #"(\S+): capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (-?\d+)")

(defn- parse-ingredient [s]
  (let [[_ name & values] (re-find ingredient-format s)
        [capacity durability flavor texture calories] (map read-string values)]
    {:name name
     :ingredient {:capacity capacity
                  :durability durability
                  :flavor flavor
                  :texture texture
                  :calories calories}}))

(defn map-vals [f m]
  (zipmap (keys m) (map f (vals m))))

(defn amount [{:keys [count ingredient]}]
  (map-vals (partial * count) ingredient))

(defn combine [receipe]
  (->> receipe
       (map amount)
       (apply (partial merge-with +))))

(defn score [receipe]
  (->> (dissoc (combine receipe) :calories)
       (vals)
       (map #(if (neg? %) 0 %))
       (reduce *)))
