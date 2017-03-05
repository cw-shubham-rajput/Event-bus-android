package com.example.shubham.eventbus_demo;

import android.content.Context;

/**
 * Created by shubh on 2/22/2017.
 */

public class Events {

    // Event used to send message from fragment to activity.
    public static class FragmentActivityMessage {
        private String message;
        public FragmentActivityMessage(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }

    // Event used to send message from activity to fragment.
    public static class ActivityFragmentMessage {
        private String message;
        public ActivityFragmentMessage(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }

    // Event used to send message from activity to activity.
    public static class ActivityActivityMessage {
        private String message;
        public ActivityActivityMessage(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }

    public static class AppConstants {
        String responce = "";
        String from = "";

        public AppConstants(Context context) {

        }

        public AppConstants(String responce, String from){
            this.responce = responce;
            this.from = from;
        }

        public void CallDB(Context context, String from){
            Constants.call_db call_db = new Constants.call_db(context,from);
            call_db.execute();
        }

        public String getResponce() {
            return responce;
        }

        public String getAction() {
            return from;
        }
    }

}

