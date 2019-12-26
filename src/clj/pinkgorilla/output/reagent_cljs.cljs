(ns pinkgorilla.output.reagent-cljs
  (:require
   [reagent.core :as reagent :refer [atom]]
   [taoensso.timbre :refer-macros (info)]
   [pinkgorilla.ui.pinkie :refer [resolve-functions]]))


(defn output-reagent-cljs
  [output _]
  (let [map-keywords (:map-kewords output)
        component (:reagent output)
        _ (info "map-keywords: " map-keywords " reagent component: " component)
        component (if map-keywords (resolve-functions component) component)]
    component
    #_(reagent/create-class
       {:display-name "output-reagent-cljs"
        :reagent-render (fn []
                          [:div.reagent
                           component])})))
