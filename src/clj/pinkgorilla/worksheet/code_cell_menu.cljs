(ns pinkgorilla.worksheet.code-cell-menu
  (:require
   [re-frame.core :refer [dispatch]]))


(defn cell-menu [segment]
  [:div {:class "font-sans flex flex-col text-center sm:flex-row sm:text-left sm:justify-between  px-6 bg-white sm:items-baseline w-full"}
   
   ;; Kernel (clj/cljs)
   [:div.mb-1.bg-yellow-300.shadow
    [:p.text-lg.p-1  {:on-click #(dispatch [:app:kernel-toggle])}
     (:kernel @segment)]]

   [:div.bg-green-700.mt-1.mb-1.h-8
   ;[:p {:class "text-lg no-underline text-grey-darkest hover:text-blue-dark ml-2"} "One" ]
    
    [:a {:class "block lg:inline-block mt-0 text-teal-200 hover:bg-orange-500 mr-4 p-1"
         :on-click #(do
                      (dispatch [:worksheet:evaluate])
                      ;(rf/dispatch [::events/set-navbar-menu-active? false])
                      )}
     "evaluate"]

    [:a {:class "block lg:inline-block mt-0 text-teal-200 hover:bg-orange-500 mr-4 p-1"
         :on-click #(do
                      (dispatch [:worksheet:clear-output])
                      ;(rf/dispatch [::events/set-navbar-menu-active? false])
                      )}
     "clear output"]]

   ;; Move Segment around.
   [:div.text-lg
    [:a {:class "block mt-4 lg:inline-block lg:mt-0 text-teal-200 hover:orange-500 mr-4 blue-700"
         :on-click #(dispatch [:worksheet:moveUp])}
     [:i.fas.fa-arrow-circle-up]
     ]
    [:a {:class "block mt-4 lg:inline-block lg:mt-0 text-teal-200 hover:orange-500 mr-4"
         :on-click #(dispatch [:worksheet:moveDown])}
       [:i.fas.fa-arrow-circle-down]
     ]]

   ])