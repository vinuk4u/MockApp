package com.iv.mockapp.fragments;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.iv.mockapp.R;
import com.iv.mockapp.activities.MAMockAppActivity;
import com.iv.mockapp.adapters.MASpinnerAdapter;
import com.iv.mockapp.controllers.MAAppController;
import com.iv.mockapp.models.MALocationModel;
import com.iv.mockapp.models.MATransportInfoModel;
import com.iv.mockapp.models.response.MAResponseArrayModel;
import com.iv.mockapp.models.response.MAResponseModel;
import com.iv.mockapp.utils.MANetworkUtil;
import com.iv.mockapp.utils.helpers.MAVolleyErrorHelper;
import com.iv.mockapp.utils.view.MAViewUtil;
import com.iv.mockapp.webservice.MAGsonRequest;
import com.iv.mockapp.webservice.constants.MANetworkConstants;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vineeth on 11/09/16
 */
public class MAScenario2Fragment extends Fragment implements Response.ErrorListener,
        Response.Listener<MAResponseArrayModel>, AdapterView.OnItemSelectedListener {

    @Bind(R.id.root_linearlayout)
    LinearLayout mRootLinearlayout;
    @Bind(R.id.name_spinner)
    Spinner mNameSpinner;
    @Bind(R.id.transport_info_textview)
    TextView mTransportInfoTextView;
    @Bind(R.id.transport_info_car_textview)
    TextView mTransportInfoCarTextView;
    @Bind(R.id.transport_info_train_textview)
    TextView mTransportInfoTrainTextView;
    @Bind(R.id.navigate_button)
    Button mNavigateButton;
    @Bind(R.id.location_imageview)
    ImageView mLocationImageView;
    private RequestQueue mRequestQueue;
    private MAGsonRequest<MAResponseArrayModel> mGsonRequest;

    public MAScenario2Fragment() {
    }

    public static MAScenario2Fragment newInstance() {
        return new MAScenario2Fragment();
    }

    @OnClick(R.id.location_imageview)
    void onLocationClick() {
        final MAResponseModel item = (MAResponseModel) mNameSpinner.getSelectedItem();
        if (item != null) {
            MALocationModel locationModel = item.getLocation();
            if (locationModel != null) {
                navigate(locationModel);
            }
        }
    }

    @OnClick(R.id.navigate_button)
    void onNavigateClick() {
        final MAResponseModel item = (MAResponseModel) mNameSpinner.getSelectedItem();
        if (item != null) {
            MALocationModel locationModel = item.getLocation();
            if (locationModel != null) {
                navigate(locationModel);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_scenario2_layout, container, false);

        ButterKnife.bind(this, rootView);

        initViews();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fetchJson();
    }

    private void initViews() {
        mTransportInfoTextView.setText(null);
        mTransportInfoCarTextView.setText(null);
        mTransportInfoTrainTextView.setText(null);

        mLocationImageView.setVisibility(View.GONE);
        mNavigateButton.setVisibility(View.GONE);
    }

    private void fetchJson() {
        if (!MANetworkUtil.hasInternetAccess(getActivity())) {
            showJsonResponseErrorSnackbar(null, getString(R.string.no_internet_connection_available));
            return;
        }

        mRequestQueue = MAAppController.getInstance().getRequestQueue();

        MAViewUtil.showProgressDialog(getActivity(),
                null, getString(R.string.downloading), new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (mGsonRequest != null) {
                            mRequestQueue.cancelAll(mGsonRequest);
                            mGsonRequest.cancel();
                            dialog.dismiss();
                        }
                    }
                });

        final String url = MANetworkConstants.JSON_URL;
        mGsonRequest = new MAGsonRequest<>(Request.Method.GET, url, this, this);

        mRequestQueue.add(mGsonRequest);
    }

    private void setLocationInfo(final MATransportInfoModel transportInfoModel) {
        mTransportInfoTextView.setText(
                getString(R.string.mode_of_transport));

        // Car Info
        if (transportInfoModel.getCar() == null) {
            mTransportInfoCarTextView.setVisibility(View.GONE);
        } else {
            final String car = getString(R.string.mode_of_transport_car) + " " + transportInfoModel.getCar();
            mTransportInfoCarTextView.setText(car);
            mTransportInfoCarTextView.setVisibility(View.VISIBLE);
        }

        // Train Info
        if (transportInfoModel.getTrain() == null) {
            mTransportInfoTrainTextView.setVisibility(View.GONE);
        } else {
            final String train = getString(R.string.mode_of_transport_train) + " " + transportInfoModel.getTrain();
            mTransportInfoTrainTextView.setText(train);
            mTransportInfoTrainTextView.setVisibility(View.VISIBLE);
        }
    }

    private void showJsonResponseErrorSnackbar(final View view, final String message) {
        ((MAMockAppActivity) getActivity()).showIndefiniteSnackBar(
                view != null ? view : mRootLinearlayout, message,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fetchJson();
                    }
                });
    }

    private void showMap(final MALocationModel locationModel) {
        mLocationImageView.setImageBitmap(null);

        final String googleMapUrl = getGoogleMapsUrl(
                String.valueOf(locationModel.getLatitude()),
                String.valueOf(locationModel.getLongitude()));
        ImageRequest irGmap = new ImageRequest(googleMapUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                mLocationImageView.setImageBitmap(response);
            }
        }, 0, 0, null, null);
        RequestQueue rqGmap = Volley.newRequestQueue(getActivity());
        rqGmap.add(irGmap);
        rqGmap.start();

        mLocationImageView.setVisibility(View.VISIBLE);
        mNavigateButton.setVisibility(View.VISIBLE);
    }

    private String getGoogleMapsUrl(final String latitude, final String longitude) {
        //// Reference
        // https://developers.google.com/maps/documentation/staticmaps/

        final String prefix = "https://maps.googleapis.com/maps/api/staticmap?center=";
        final String postfix = "&zoom=12&size=600x300&markers=color:red%7C";

        return prefix + latitude + "," + longitude + postfix + latitude + "," + longitude;
    }

    private void navigate(final MALocationModel location) {
        try {
            final String uri = String.format(Locale.ENGLISH, "geo:%f,%f",
                    Float.parseFloat(String.valueOf(location.getLatitude())),
                    Float.parseFloat(String.valueOf(location.getLongitude())));
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            MAViewUtil.showSnackBar(mRootLinearlayout, getString(R.string.maps_not_found));
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        MAViewUtil.dismissProgressDialog();
        showJsonResponseErrorSnackbar(null, MAVolleyErrorHelper.getMessage(error, getActivity()));
    }

    @Override
    public void onResponse(MAResponseArrayModel response) {
        MAViewUtil.dismissProgressDialog();

        if (response != null) {
            // Creating adapter for spinner
            MASpinnerAdapter adapter = new MASpinnerAdapter(getActivity(),
                    android.R.layout.simple_spinner_item, android.R.layout.simple_spinner_dropdown_item,
                    response.getResponseModels());

            // Apply the adapter to the spinner
            mNameSpinner.setAdapter(adapter);

            mNameSpinner.setOnItemSelectedListener(MAScenario2Fragment.this);
        } else {
            showJsonResponseErrorSnackbar(null, getString(R.string.unknown_error));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final MAResponseModel item = (MAResponseModel) mNameSpinner.getAdapter().getItem(position);

        if (item == null) return;

        final MATransportInfoModel transportInfoModel = item.getTransportInfo();

        if (transportInfoModel == null) return;

        setLocationInfo(transportInfoModel);

        mLocationImageView.setVisibility(View.GONE);
        mNavigateButton.setVisibility(View.GONE);
        final MALocationModel locationModel = item.getLocation();
        if (locationModel != null) {
            showMap(locationModel);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
