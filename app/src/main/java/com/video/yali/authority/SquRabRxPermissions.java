package com.video.yali.authority;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;

public class SquRabRxPermissions {
    static final String TAG = SquRabRxPermissions.class.getSimpleName();
    static final Object TRIGGER = new Object();
    @VisibleForTesting
    Lazy<SquRabRxPermissionsFragment> mRxPermissionsFragment;

    public SquRabRxPermissions(@NonNull FragmentActivity activity) {
        this.mRxPermissionsFragment = this.getLazySingleton(activity.getSupportFragmentManager());
    }

    public SquRabRxPermissions(@NonNull Fragment fragment) {
        this.mRxPermissionsFragment = this.getLazySingleton(fragment.getChildFragmentManager());
    }

    @NonNull
    private Lazy<SquRabRxPermissionsFragment> getLazySingleton(@NonNull final FragmentManager fragmentManager) {
        return new Lazy<SquRabRxPermissionsFragment>() {
            private SquRabRxPermissionsFragment rxPermissionsFragment;

            public synchronized SquRabRxPermissionsFragment get() {
                if (this.rxPermissionsFragment == null) {
                    this.rxPermissionsFragment = SquRabRxPermissions.this.getRxPermissionsFragment(fragmentManager);
                }

                return this.rxPermissionsFragment;
            }
        };
    }

    private SquRabRxPermissionsFragment getRxPermissionsFragment(@NonNull FragmentManager fragmentManager) {
        SquRabRxPermissionsFragment rxPermissionsFragment = this.findRxPermissionsFragment(fragmentManager);
        boolean isNewInstance = rxPermissionsFragment == null;
        if (isNewInstance) {
            rxPermissionsFragment = new SquRabRxPermissionsFragment();
            fragmentManager.beginTransaction().add(rxPermissionsFragment, TAG).commitNowAllowingStateLoss();
        }

        return rxPermissionsFragment;
    }

    private SquRabRxPermissionsFragment findRxPermissionsFragment(@NonNull FragmentManager fragmentManager) {
        return (SquRabRxPermissionsFragment) fragmentManager.findFragmentByTag(TAG);
    }

    public void setLogging(boolean logging) {
        ((SquRabRxPermissionsFragment) this.mRxPermissionsFragment.get()).setLogging(logging);
    }

    public <T> ObservableTransformer<T, Boolean> ensure(final String... permissions) {
        return new ObservableTransformer<T, Boolean>() {
            public ObservableSource<Boolean> apply(Observable<T> o) {
                return SquRabRxPermissions.this.request(o, permissions).buffer(permissions.length).flatMap(new Function<List<Permission>, ObservableSource<Boolean>>() {
                    public ObservableSource<Boolean> apply(List<Permission> permissionsx) {
                        if (permissionsx.isEmpty()) {
                            return Observable.empty();
                        } else {
                            Iterator var2 = permissionsx.iterator();

                            Permission p;
                            do {
                                if (!var2.hasNext()) {
                                    return Observable.just(true);
                                }

                                p = (Permission) var2.next();
                            } while (p.granted);

                            return Observable.just(false);
                        }
                    }
                });
            }
        };
    }

    public <T> ObservableTransformer<T, Permission> ensureEach(final String... permissions) {
        return new ObservableTransformer<T, Permission>() {
            public ObservableSource<Permission> apply(Observable<T> o) {
                return SquRabRxPermissions.this.request(o, permissions);
            }
        };
    }

    public <T> ObservableTransformer<T, Permission> ensureEachCombined(final String... permissions) {
        return new ObservableTransformer<T, Permission>() {
            public ObservableSource<Permission> apply(Observable<T> o) {
                return SquRabRxPermissions.this.request(o, permissions).buffer(permissions.length).flatMap(new Function<List<Permission>, ObservableSource<Permission>>() {
                    public ObservableSource<Permission> apply(List<Permission> permissionsX) {
                        return permissionsX.isEmpty() ? Observable.<Permission>empty() :
                                Observable.just(new Permission(permissionsX));
                    }
                });
            }
        };
    }

    public Observable<Boolean> request(String... permissions) {
        return Observable.just(TRIGGER).compose(this.ensure(permissions));
    }

    public Observable<Permission> requestEach(String... permissions) {
        return Observable.just(TRIGGER).compose(this.ensureEach(permissions));
    }

    public Observable<Permission> requestEachCombined(String... permissions) {
        return Observable.just(TRIGGER).compose(this.ensureEachCombined(permissions));
    }

    private Observable<Permission> request(Observable<?> trigger, final String... permissions) {
        if (permissions != null && permissions.length != 0) {
            return this.oneOf(trigger, this.pending(permissions)).flatMap(new Function<Object, Observable<Permission>>() {
                public Observable<Permission> apply(Object o) {
                    return SquRabRxPermissions.this.requestImplementation(permissions);
                }
            });
        } else {
            throw new IllegalArgumentException("RxPermissions.request/requestEach requires at least one input permission");
        }
    }

    private Observable<?> pending(String... permissions) {
        String[] var2 = permissions;
        int var3 = permissions.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String p = var2[var4];
            if (!((SquRabRxPermissionsFragment) this.mRxPermissionsFragment.get()).containsByPermission(p)) {
                return Observable.empty();
            }
        }

        return Observable.just(TRIGGER);
    }

    private Observable<?> oneOf(Observable<?> trigger, Observable<?> pending) {
        return trigger == null ? Observable.just(TRIGGER) : Observable.merge(trigger, pending);
    }

    @TargetApi(23)
    private Observable<Permission> requestImplementation(String... permissions) {
        List<Observable<Permission>> list = new ArrayList(permissions.length);
        List<String> unrequestedPermissions = new ArrayList();
        String[] unrequestedPermissionsArray = permissions;
        int var5 = permissions.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            String permission = unrequestedPermissionsArray[var6];
            ((SquRabRxPermissionsFragment) this.mRxPermissionsFragment.get()).log("Requesting permission " + permission);
            if (this.isGranted(permission)) {
                list.add(Observable.just(new Permission(permission, true, false)));
            } else if (this.isRevoked(permission)) {
                list.add(Observable.just(new Permission(permission, false, false)));
            } else {
                PublishSubject<Permission> subject = ((SquRabRxPermissionsFragment) this.mRxPermissionsFragment.get()).getSubjectByPermission(permission);
                if (subject == null) {
                    unrequestedPermissions.add(permission);
                    subject = PublishSubject.create();
                    ((SquRabRxPermissionsFragment) this.mRxPermissionsFragment.get()).setSubjectForPermission(permission, subject);
                }

                list.add(subject);
            }
        }

        if (!unrequestedPermissions.isEmpty()) {
            unrequestedPermissionsArray = (String[]) unrequestedPermissions.toArray(new String[unrequestedPermissions.size()]);
            this.requestPermissionsFromFragment(unrequestedPermissionsArray);
        }

        return Observable.concat(Observable.fromIterable(list));
    }

    public Observable<Boolean> shouldShowRequestPermissionRationale(Activity activity, String... permissions) {
        return !this.isMarshmallow() ? Observable.just(false) : Observable.just(this.shouldShowRequestPermissionRationaleImplementation(activity, permissions));
    }

    @TargetApi(23)
    private boolean shouldShowRequestPermissionRationaleImplementation(Activity activity, String... permissions) {
        String[] var3 = permissions;
        int var4 = permissions.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String p = var3[var5];
            if (!this.isGranted(p) && !activity.shouldShowRequestPermissionRationale(p)) {
                return false;
            }
        }

        return true;
    }

    @TargetApi(23)
    void requestPermissionsFromFragment(String[] permissions) {
        ((SquRabRxPermissionsFragment) this.mRxPermissionsFragment.get()).log("requestPermissionsFromFragment " + TextUtils.join(", ", permissions));
        ((SquRabRxPermissionsFragment) this.mRxPermissionsFragment.get()).requestPermissions(permissions);
    }

    public boolean isGranted(String permission) {
        return !this.isMarshmallow() || ((SquRabRxPermissionsFragment) this.mRxPermissionsFragment.get()).isGranted(permission);
    }

    public boolean isRevoked(String permission) {
        return this.isMarshmallow() && ((SquRabRxPermissionsFragment) this.mRxPermissionsFragment.get()).isRevoked(permission);
    }

    boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= 23;
    }

    void onRequestPermissionsResult(String[] permissions, int[] grantResults) {
        ((SquRabRxPermissionsFragment) this.mRxPermissionsFragment.get()).onRequestPermissionsResult(permissions, grantResults, new boolean[permissions.length]);
    }

    @FunctionalInterface
    public interface Lazy<V> {
        V get();
    }
}



