/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/9/18 9:54 AM
 */

package sud_tanj.com.phr_android;

import android.content.Context;

import com.heinrichreimersoftware.androidissuereporter.IssueReporterLauncher;

import sud_tanj.com.phr_android.Custom.Global;

public class ReportView {
    public static void launch(Context context) {
        IssueReporterLauncher.forTarget("sudtanj", "CPP-Collection")
                // [Recommended] Theme to use for the reporter.
                // (See #theming for further information.)
                .theme(R.style.AppTheme)
                // [Optional] Auth token to open issues if users don't have a GitHub account
                // You can register a bot account on GitHub and copy ist OAuth2 token here.
                // (See #how-to-create-a-bot-key for further information.)
                .guestToken("8da97147f8a7c55c075ef109b90b3d0b85e76445")
                // [Optional] Force users to enter an email adress when the report is sent using
                // the guest token.
                .guestEmailRequired(Boolean.FALSE)
                // [Optional] Set a minimum character limit for the description to filter out
                // empty reports.
                .minDescriptionLength(20)
                // [Optional] Include other relevant info in the bug report (like custom variables)
                .putExtraInfo("userId", Global.getFireBaseUser().getUid())
                // [Optional] Disable back arrow in toolbar
                .homeAsUpEnabled(Boolean.TRUE)
                .launch(context);
    }
}
