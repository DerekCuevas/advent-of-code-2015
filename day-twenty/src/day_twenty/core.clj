(ns day-twenty.core)

(def presents-multiplier 10)

(def target-presents 29000000)

(defn divisible-by? [den x]
  (zero? (mod x den)))

(defn divisors [n]
  (filter #(divisible-by? % n) (range 1 (inc n))))

(defn sum-divisors [n]
  (reduce + (divisors n)))

(defn find-house [x]
  (loop [index 450000]
    (let [presents (sum-divisors index)]
      (if (>= presents x)
        index
        (recur (inc index))))))

;; (find-house (/ target-presents 10))
