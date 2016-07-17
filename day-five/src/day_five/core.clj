(ns day-five.core
  (:require [clojure.string :refer [includes?]])
  (:gen-class))

(defn- contains-at-least-three-vowels? [s]
  (-> #{\a \e \i \o \u}
      (filter s)
      count
      (>= 3)))

(defn- any-twice-in-a-row? [s]
  (->> (partition-by identity s)
       (some #(>= (count %) 2))))

(defn- not-in-blacklist? [s]
  (->> ["ab" "cd" "pq" "xy"]
       (map (partial includes? s))
       (not-any? true?)))

(defn- nice? [s]
  (->> [contains-at-least-three-vowels?
        any-twice-in-a-row?
        not-in-blacklist?]
       (map #(% s))
       (every? true?)))

(defn nice-count [ss]
  (count (filter nice? ss)))