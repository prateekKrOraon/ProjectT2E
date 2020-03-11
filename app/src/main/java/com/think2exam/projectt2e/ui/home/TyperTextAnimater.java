package com.think2exam.projectt2e.ui.home;

import android.os.Handler;

import com.hanks.htextview.base.AnimationListener;
import com.hanks.htextview.base.HTextView;
import com.hanks.htextview.typer.TyperTextView;

public class TyperTextAnimater {

    TyperTextView typerTextView;
    public TyperTextAnimater(TyperTextView id)
    {
        typerTextView = id;
    }

    public void setTyperTextView()
    {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                typerTextView.animateText("Do you want to find\nright colleges for you?");
                typerTextView.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationEnd(HTextView hTextView) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        hTextView.animateText("Think2Exam is here for you");

                        hTextView.setAnimationListener(new AnimationListener() {
                            @Override
                            public void onAnimationEnd(HTextView hTextView) {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                hTextView.animateText("600+ Engineering Colleges\n400+ Medical Colleges\n1000+ Other Colleges");
                                hTextView.setAnimationListener(new AnimationListener() {
                                    @Override
                                    public void onAnimationEnd(HTextView hTextView) {

                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        typerTextView.animateText("Do you want to find\nright colleges for you?");
                                        typerTextView.setAnimationListener(new AnimationListener() {
                                            @Override
                                            public void onAnimationEnd(HTextView hTextView) {
                                                try {
                                                    Thread.sleep(1000);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                                hTextView.animateText("Think2Exam is here for you");

                                                hTextView.setAnimationListener(new AnimationListener() {
                                                    @Override
                                                    public void onAnimationEnd(HTextView hTextView) {
                                                        try {
                                                            Thread.sleep(1000);
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
                                                        hTextView.animateText("600+ Engineering Colleges\n400+ Medical Colleges\n1000+ Other Colleges");
                                                        hTextView.setAnimationListener(new AnimationListener() {
                                                            @Override
                                                            public void onAnimationEnd(HTextView hTextView) {
                                                                try {
                                                                    Thread.sleep(1000);
                                                                } catch (InterruptedException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                typerTextView.animateText("Do you want to find\nright colleges for you?");
                                                                typerTextView.setAnimationListener(new AnimationListener() {
                                                                    @Override
                                                                    public void onAnimationEnd(HTextView hTextView) {
                                                                        try {
                                                                            Thread.sleep(1000);
                                                                        } catch (InterruptedException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                        hTextView.animateText("Think2Exam is here for you");

                                                                        hTextView.setAnimationListener(new AnimationListener() {
                                                                            @Override
                                                                            public void onAnimationEnd(HTextView hTextView) {
                                                                                try {
                                                                                    Thread.sleep(1000);
                                                                                } catch (InterruptedException e) {
                                                                                    e.printStackTrace();
                                                                                }
                                                                                hTextView.animateText("600+ Engineering Colleges\n400+ Medical Colleges\n1000+ Other Colleges");
                                                                                hTextView.setAnimationListener(new AnimationListener() {
                                                                                    @Override
                                                                                    public void onAnimationEnd(HTextView hTextView) {
                                                                                        try {
                                                                                            Thread.sleep(1000);
                                                                                        } catch (InterruptedException e) {
                                                                                            e.printStackTrace();
                                                                                        }
                                                                                        typerTextView.animateText("");

                                                                                    }
                                                                                });
                                                                            }
                                                                        });
                                                                    }
                                                                });


                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        });


                                    }
                                });
                            }
                        });
                    }
                });

            }
        });


        typerTextView.setTyperSpeed(80);
    }

}
