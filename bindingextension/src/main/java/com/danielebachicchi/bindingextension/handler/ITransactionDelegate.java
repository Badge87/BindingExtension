package com.danielebachicchi.bindingextension.handler;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public interface ITransactionDelegate {
    void onPrepareReplaceTransaction(FragmentTransaction transaction, Fragment oldFragment, Fragment newFragment);
}
