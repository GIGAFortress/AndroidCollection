package gigafortress.simplemvp.view;

import gigafortress.simplemvp.bean.User;

/**
 * UserLoginPresenter实例化并使用该接口，MainActivity实现该接口
 */
public interface IUserLoginView {
    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity(User user);

    void showFailedError();
}
