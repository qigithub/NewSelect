package com.qi.newselect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongqi on 2017/1/12.
 */

public class TestBuilder {
    private String name = "";

    public TestBuilder() {
        this(new Builder());
    }

    TestBuilder(Builder builder) {
        this.name = builder.name;
    }

    public static final class Builder {
        private String name = "";

        public Builder() {
            this.name = "name";
        }

        Builder(TestBuilder tB) {
            this.name = tB.name;
        }


        public TestBuilder build() {
            return new TestBuilder(this);
        }

        public void testQueue() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ArrayList<String> mQueue = null;
                    if (mQueue == null)
                        mQueue = new ArrayList<>(10);
                    int size = mQueue.size();
                    while (mQueue != null && !mQueue.isEmpty()) {
                        for (int i = 0; i < size; i++) {
                            if (mQueue.get(i).equals("rv")) {
                                mQueue.remove(i);
                                size--;
                                i--;
                            }
                        }
                    }
                }
            });
        }

    }


}
