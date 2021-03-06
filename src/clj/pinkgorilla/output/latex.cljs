(ns pinkgorilla.output.latex
  (:require
   [cljs-uuid-utils.core :as uuid]
   [reagent.core :as reagent]
   [pinkgorilla.output.mathjax :refer [queue-mathjax-rendering]]
   [pinkgorilla.output.hack :refer [value-wrap]]
   ))


(defn output-latex
  [output seg-id]
  (let [uuid (uuid/uuid-string (uuid/make-random-uuid))
        span-kw (keyword (str "span#" uuid))]
    (reagent/create-class
     {:component-did-mount  (fn [this]
                              (queue-mathjax-rendering uuid))
      :component-did-update (fn [this old-argv])
      :reagent-render       (fn []
                              [value-wrap
                               (get output :value)
                               [span-kw {:class                   "latex-span"
                                         :dangerouslySetInnerHTML {:__html (str "@@" (:content output) "@@")}}]])})))

;;     return wrapWithValue(data, "<span class='latex-span' id='" + uuid + "'>@@" + data.content + "@@</span>");
