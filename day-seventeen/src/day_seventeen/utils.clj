(ns day-seventeen.utils)

(defn- index-combinations
  [n cnt]
  (lazy-seq
   (let [c (vec (cons nil (for [j (range 1 (inc n))] (+ j cnt (- (inc n)))))),
         iter-comb
         (fn iter-comb [c j]
           (if (> j n) nil
               (let [c (assoc c j (dec (c j)))]
                 (if (< (c j) j) [c (inc j)]
                     (loop [c c, j j]
                       (if (= j 1) [c j]
                           (recur (assoc c (dec j) (dec (c j))) (dec j)))))))),
         step
         (fn step [c j]
           (cons (rseq (subvec c 1 (inc n)))
                 (lazy-seq (let [next-step (iter-comb c j)]
                             (when next-step (step (next-step 0) (next-step 1)))))))]
     (step c 1))))

;; Adapted from https://github.com/clojure/math.combinatorics/blob/4c3d26eec2206e09b8b8c305bfed921966bce6f6/src/main/clojure/clojure/math/combinatorics.cljc#L177
(defn- combinations-non-unique
  [items t]
  (let [v-items (vec (reverse items))]
    (if (zero? t) (list ())
        (let [cnt (count items)]
          (cond (> t cnt) nil
                (= t 1) (for [item (distinct items)] (list item))
                :else (if (= t cnt)
                        (list (seq items))
                        (map #(map v-items %) (index-combinations t cnt))))))))

(defn all-combinations [seq]
  (mapcat #(combinations-non-unique seq %) (range 1 (inc (count seq)))))