(ns pinkgorilla.dialog.notifications
  (:require
   [re-frame.core :as rf :include-macros true]
   [pinkgorilla.subs]
   [pinkgorilla.notifications :refer [notification-types]]
   ))

;; stolen from:
;; https://github.com/baskeboler/cljs-karaoke-client/blob/master/src/main/cljs_karaoke/notifications.cljs


;<div class= "bg-orange-100 border-l-4 border-orange-500 text-orange-700 p-4" role= "alert" >
;<p class= "font-bold" >Be Warned</p>
;<p>Something not ideal might be happening.</p>
;</div>


(defn type-css-class [notification-type]
  (assert (notification-types notification-type))
  ;(str "is-" (symbol notification-type))
  (case notification-type
    :danger "bg-red-100 border-l-4 border-red-500 text-red-700 p-4"
    :warning "bg-orange-100 border-l-4 border-orange-500 text-orange-700 p-4"
    :info "bg-blue-100 border-l-4 border-blue-500 text-blue-700 p-4"
    "bg-orange-100 border-l-4 border-orange-500 text-orange-700 p-4" 
    ))


(defn notification-component [n]
  [:div.notification
   {:key (str "notification-" (:id n))
    :class (type-css-class (:type n))
    ;:class 
    :role "alert"
    }
   [:button.delete
    {:on-click #(rf/dispatch [:notification-dismiss (:id n)])}]
   (:text n)])


(defn ^export notifications-container-component []
  (let [nots-subs (rf/subscribe [:notifications])]
    [:div.notifications-container
     (when (not-empty @nots-subs)
       (doall
        (for [n @nots-subs]
          ^{:key (str "notify-" (:id n))}
          [notification-component n])))]))