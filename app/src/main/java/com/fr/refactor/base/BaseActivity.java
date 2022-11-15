/*
package com.fr.refactor.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.fr.refactor.MyApp;
import com.fr.refactor.di.component.DaggerActivityComponent;
import com.fr.refactor.di.mudule.activitymodule.ActivityModule;
import com.fr.refactor.utils.common.CommonUtils;
import com.fr.refactor.utils.network.NetworkUtils;

import java.util.Map;

import javax.inject.Inject;

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity implements BaseFragment.Callback {

    // TODO
    // this can probably depend on isLoading variable of BaseViewModel,
    // since its going to be common for all the activities
    private ProgressDialog mProgressDialog;

    protected T binding;

    @Inject
    protected V viewModel;

    */
/**
     * Override for set binding variable
     *
     * @return variable id
     *//*

    public abstract int getBindingVariable();

    */
/**
     * @return layout resource id
     *//*

    public abstract
    @LayoutRes
    int getLayoutId();


    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    */
/**
     * redirect to another activity
     * @param cls
     *//*

    public void turn2Activity(Class<?> cls) {
        Intent i = new Intent(this, cls);
        startActivity(i);
    }


    */
/**
     * redirect to another activity for result
     * @param cls
     *//*

    public void turn2ActivityForResult(Class<?> cls){
        Intent i = new Intent(this, cls);
        resultLauncher.launch(i);
    }

    */
/**
     * redirect to another activity for result
     * @param intent
     *//*

    public void turn2ActivityForResult(Intent intent){
        resultLauncher.launch(intent);
    }

    public String getVersionName() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    */
/**
     * @param cls
     * @param bundle
     *//*

    public void turn2Activity(Class<?> cls, Bundle bundle) {
        Intent i = new Intent(this, cls);
        i.putExtras(bundle);
        startActivity(i);
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


 */
/*   @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }*//*


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection(getBuildComponent());
        super.onCreate(savedInstanceState);
        performDataBinding();
        onViewCreated(savedInstanceState);
    }

    protected T getViewDataBinding() {
        return binding;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    protected void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }



    protected boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }


    private ActivityComponent getBuildComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(((MyApp) getApplication()).appComponent)
                .activityModule(new ActivityModule(this))
                .build();
    }

    protected abstract void performDependencyInjection(ActivityComponent buildComponent);

    protected abstract void onViewCreated(Bundle savedInstanceState);


    @TargetApi(Build.VERSION_CODES.M)
    public void requestMultiplePermissionsSafely(String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            multiplePermissionLauncher.launch(permissions);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestSinglePermissionsSafely(String permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            singlePermissionLauncher.launch(permissions);
        }
    }



    protected void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    protected void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    private void performDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setVariable(getBindingVariable(), viewModel);
        binding.executePendingBindings();
    }

    protected void onPermissionResult(Map<String, Boolean> result){

    }

    protected void onPermissionResult(boolean isGranted){

    }

    protected void onActivityResultListener(ActivityResult result) {

    }

    private final ActivityResultLauncher<String[]> multiplePermissionLauncher =
            registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            this::onPermissionResult);


    private final ActivityResultLauncher<String> singlePermissionLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    this::onPermissionResult);


    private final ActivityResultLauncher<Intent> resultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    this::onActivityResultListener);




}
*/
