package s4y.itag.itag;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import s4y.itag.ITagApplication;
import s4y.itag.R;
import s4y.itag.ble.BLEScanResult;

public class ITagDefault implements ITagInterface, Serializable {
private static final long serialVersionUID = 1575220516;
@NonNull
    private final String id;
    @NonNull
    private String name;
    @NonNull
    private TagColor color;
    private boolean alert;
    private TagAlertMode alertMode;
    private TagConnectionMode connectionMode;
    private int alertDelay;
    private boolean shakingOnConnectDisconnect = false;

    public ITagDefault(@NonNull String id, @Nullable String name, @Nullable TagColor color, @Nullable Boolean alert, @Nullable Integer alertDelay, @Nullable TagAlertMode alertMode, @Nullable TagConnectionMode connectionMode) {
        this.id = id;
        this.name = name == null ? ITagApplication.context.getString(R.string.unknown):name;
        this.color = color == null? TagColor.black : color;
        this.alertMode = alertMode == null ? TagAlertMode.alertOnDisconnect : alertMode;
        this.connectionMode = connectionMode == null ? TagConnectionMode.connect : connectionMode;
        Log.d("ingo", "we set mode to " + alertMode);
        this.alert = alert == null ? false : alert;
        this.alertDelay = alertDelay == null ? 5: alertDelay;
    }

    public ITagDefault(@NonNull BLEScanResult scanResult) {
        this(scanResult.id, scanResult.name == null ? "" : scanResult.name.trim(), null, null, null, null, null);
    }

    public ITagDefault(@NonNull String id, Map<String, Object> dict) {
        this(id, (String)dict.get("name"), (TagColor)dict.get("color"), (Boolean)dict.get("alert"), (Integer)dict.get("alertDelay"), (TagAlertMode) dict.get("alertMode"), (TagConnectionMode) dict.get("connectionMode"));
        Log.d("ingo", "poziva se iz dicta");
    }

    @NonNull
    @Override
    public String id() {
        return id;
    }

    @NonNull
    @Override
    public String name() {
        return name;
    }

    @Override
    public void setName(@NonNull  String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public TagColor color() {
        return color;
    }

    @NonNull
    @Override
    public TagAlertMode alertMode() {
        return alertMode;
    }

    @NonNull
    @Override
    public Boolean shakingOnConnectDisconnect() {
        return shakingOnConnectDisconnect;
    }

    @NonNull
    @Override
    public TagConnectionMode connectionMode() {
        return connectionMode;
    }

    @Override
    public void setColor(@NonNull TagColor color) {
        this.color = color;
    }

    @Override
    public void setShakingOnConnectDisconnect(@NonNull Boolean shaking) {
        this.shakingOnConnectDisconnect = shaking;
    }

    @Override
    public boolean isAlertEnabled() {
        return alert;
    }

    @Override
    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    @Override
    public int alertDelay() {
        return alertDelay;
    }

    @Override
    public void setAlertDelay(int alertDelay) {
        this.alertDelay = alertDelay;
    }

    @Override
    public void setAlertMode(TagAlertMode alertMode) {
        this.alertMode = alertMode;
    }

    @Override
    public void setConnectionMode(TagConnectionMode connectionMode) {
        this.connectionMode = connectionMode;
    }

    @Override
    public void copyFromTag(@NonNull ITagInterface tag) {
        name = tag.name();
        alert = tag.isAlertEnabled();
        color = tag.color();
    }

    @NonNull
    @Override
    public Map<String, Object> toDict() {
        return new HashMap<String, Object>(){{
            put("id", id);
            put("name", name);
            put("color", color);
            put("alert", alert);
        }};
    }
}
