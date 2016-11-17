# hexpacker-stitch-lite

The core hexagonal circle packing implementation from https://github.com/AbleEng/hexpacker ported to cljc.

```clojure
(require '[hexpacker-stitch-lite.core :as stitch])

(let [parent-radius 10
      child-radius 5
      center {:x 0 :y 0}]
  (stitch/pack-circle parent-radius child-radius center))
;; => #{{:y 0, :x 0}
;;      {:y 0, :x 10}
;;      {:y 8.660254037844386, :x 5.000000000000001}
;;      {:y 8.660254037844387, :x -4.999999999999998}
;;      {:y 2.6645352591003757e-15, :x -10}
;;      {:y -8.660254037844384, :x -5.0000000000000036}
;;      {:y -8.660254037844389, :x 4.999999999999995}
;;      {:y -7.105427357601002e-15, :x 10}}
````
