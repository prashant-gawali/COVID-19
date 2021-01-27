package com.example.covid_19.mvp;

public class MVPDemo {


    class presenterImpl implements model.OnListener,presenter{
        model model ;

        presenterImpl(model model){
            this.model = model;
        }
        @Override
        public void success() {

        }

        @Override
        public void failure() {

        }

        @Override
        public void request() {
            model.processData(this);
        }
    }
    class modelImpl implements model{

        @Override
        public void processData(OnListener onListener) {
            onListener.failure();
        }
    }
    //    view
    public interface view{
        void success();
        void failure();

    }

    public interface presenter{
        void request();
    }

    public interface model{
        void processData(OnListener onListener);
        interface OnListener{
            void success();
            void failure();
        }
    }
//    presenter

    //model code
}
