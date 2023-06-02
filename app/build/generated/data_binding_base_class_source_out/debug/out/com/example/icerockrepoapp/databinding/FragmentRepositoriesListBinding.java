// Generated by view binder compiler. Do not edit!
package com.example.icerockrepoapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import com.example.icerockrepoapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentRepositoriesListBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final Button btRetry;

  @NonNull
  public final Button btRetryConnect;

  @NonNull
  public final ConstraintLayout errorView;

  @NonNull
  public final ImageView imageView5;

  @NonNull
  public final ImageView imageView6;

  @NonNull
  public final ConstraintLayout mainView;

  @NonNull
  public final ConstraintLayout noInternetView;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final RecyclerView rcView;

  @NonNull
  public final TextView textView;

  @NonNull
  public final TextView tvConnectionError;

  @NonNull
  public final TextView tvError;

  @NonNull
  public final TextView tvSomethingError;

  private FragmentRepositoriesListBinding(@NonNull FrameLayout rootView, @NonNull Button btRetry,
      @NonNull Button btRetryConnect, @NonNull ConstraintLayout errorView,
      @NonNull ImageView imageView5, @NonNull ImageView imageView6,
      @NonNull ConstraintLayout mainView, @NonNull ConstraintLayout noInternetView,
      @NonNull ProgressBar progressBar, @NonNull RecyclerView rcView, @NonNull TextView textView,
      @NonNull TextView tvConnectionError, @NonNull TextView tvError,
      @NonNull TextView tvSomethingError) {
    this.rootView = rootView;
    this.btRetry = btRetry;
    this.btRetryConnect = btRetryConnect;
    this.errorView = errorView;
    this.imageView5 = imageView5;
    this.imageView6 = imageView6;
    this.mainView = mainView;
    this.noInternetView = noInternetView;
    this.progressBar = progressBar;
    this.rcView = rcView;
    this.textView = textView;
    this.tvConnectionError = tvConnectionError;
    this.tvError = tvError;
    this.tvSomethingError = tvSomethingError;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentRepositoriesListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentRepositoriesListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_repositories_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentRepositoriesListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bt_Retry;
      Button btRetry = rootView.findViewById(id);
      if (btRetry == null) {
        break missingId;
      }

      id = R.id.bt_RetryConnect;
      Button btRetryConnect = rootView.findViewById(id);
      if (btRetryConnect == null) {
        break missingId;
      }

      id = R.id.errorView;
      ConstraintLayout errorView = rootView.findViewById(id);
      if (errorView == null) {
        break missingId;
      }

      id = R.id.imageView5;
      ImageView imageView5 = rootView.findViewById(id);
      if (imageView5 == null) {
        break missingId;
      }

      id = R.id.imageView6;
      ImageView imageView6 = rootView.findViewById(id);
      if (imageView6 == null) {
        break missingId;
      }

      id = R.id.mainView;
      ConstraintLayout mainView = rootView.findViewById(id);
      if (mainView == null) {
        break missingId;
      }

      id = R.id.noInternetView;
      ConstraintLayout noInternetView = rootView.findViewById(id);
      if (noInternetView == null) {
        break missingId;
      }

      id = R.id.progressBar;
      ProgressBar progressBar = rootView.findViewById(id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.rcView;
      RecyclerView rcView = rootView.findViewById(id);
      if (rcView == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = rootView.findViewById(id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.tvConnectionError;
      TextView tvConnectionError = rootView.findViewById(id);
      if (tvConnectionError == null) {
        break missingId;
      }

      id = R.id.tvError;
      TextView tvError = rootView.findViewById(id);
      if (tvError == null) {
        break missingId;
      }

      id = R.id.tvSomethingError;
      TextView tvSomethingError = rootView.findViewById(id);
      if (tvSomethingError == null) {
        break missingId;
      }

      return new FragmentRepositoriesListBinding((FrameLayout) rootView, btRetry, btRetryConnect,
          errorView, imageView5, imageView6, mainView, noInternetView, progressBar, rcView,
          textView, tvConnectionError, tvError, tvSomethingError);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}