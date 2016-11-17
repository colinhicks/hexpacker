(ns hexpacker-stitch-lite.math-interop)

(def PI js/Math.PI)
(def sin js/Math.sin)
(def cos js/Math.cos)

(defn toRadians [deg]
  (/ (* deg PI) 180))
