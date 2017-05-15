package com.upreckless.support.newsapp.ui.splash;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.upreckless.support.newsapp.R;
import com.upreckless.support.newsapp.app.Injector;
import com.upreckless.support.newsapp.ui.BaseFragment;
import com.upreckless.support.newsapp.ui.BasePresenter;
import com.upreckless.support.newsapp.ui.Layout;
import com.upreckless.support.newsapp.ui.splash.dagger.SplashModule;
import com.upreckless.support.newsapp.util.Constance;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by @mistreckless on 11.05.2017. !
 */
@Layout(id= R.layout.splash_screen)
public class Splash extends BaseFragment implements SplashView {

    @Inject
    SplashPresenter presenter;
    @Bind(R.id.btn_go)
    Button btnGo;
    @Bind(R.id.edt_phone)
    EditText edtPhone;
    @Bind(R.id.img_thumb_country)
    ImageView imgThumbCountry;

    public static Splash newInstance(){
        return new Splash();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.controlBtnGo(RxTextView.textChanges(edtPhone));
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected Object getRouter() {
        return getActivity();
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void inject() {
        Injector.getInstance().plusSplashComponent(new SplashModule()).inject(this);
    }

    @Override
    public void setGoBtnEnabled(boolean enabled) {
        btnGo.setEnabled(enabled);
    }

    @Override
    public void showCountryThumb(Constance.CountryName countryName) {
        switch (countryName) {
            case RUSSIA:
                setCountryThumbDrawable(R.mipmap.ru);
                break;
            case UKRAIN:
                setCountryThumbDrawable(R.mipmap.ua);
                break;
            case BELORUSSIA:
                setCountryThumbDrawable(R.mipmap.by);
                break;
            case ARMENIA:
                setCountryThumbDrawable(R.mipmap.am);
                break;
            case KYRGYZSTAN:
                setCountryThumbDrawable(R.mipmap.kg);
                break;
            case UNKNOWN:
                imgThumbCountry.setVisibility(View.INVISIBLE);
                break;
            default:
                imgThumbCountry.setVisibility(View.INVISIBLE);
                break;
        }
    }


    @OnClick(R.id.btn_go)
    void onBtnGoClick(View view) {
        if (view.isEnabled())
            presenter.btnGoClicked(edtPhone.getText().toString());
    }

    private void setCountryThumbDrawable(@DrawableRes int resId) {
        imgThumbCountry.setVisibility(View.VISIBLE);
        imgThumbCountry.setImageDrawable(getResources().getDrawable(resId));
    }
}
