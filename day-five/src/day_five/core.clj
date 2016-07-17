(ns day-five.core
  (:require [clojure.string :refer [includes?]])
  (:gen-class))

(defn- contains-at-least-three-vowels? [s]
  (>= (count (filter #{\a \e \i \o \u} s)) 3))

(defn- any-twice-in-a-row? [s]
  (->> (partition-by identity s)
       (map count)
       (some (partial <= 2))))

(defn- not-in-blacklist? [s]
  (->> ["ab" "cd" "pq" "xy"]
       (map (partial includes? s))
       (not-any? true?)))

(def ^:private rules-a
  (juxt contains-at-least-three-vowels?
        any-twice-in-a-row?
        not-in-blacklist?))

(defn- nice? [rules s]
  (every? true? (rules s)))

(defn nice-count [rules coll]
  (count (filter (partial nice? rules) coll)))

(defn nice-count-one [coll]
  (nice-count rules-a coll))
