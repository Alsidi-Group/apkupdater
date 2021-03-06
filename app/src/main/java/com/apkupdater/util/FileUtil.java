package com.apkupdater.util;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import eu.chainfire.libsuperuser.Shell;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class FileUtil
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    static public File inputStreamToCacheFile(
        Context context,
        InputStream input
    ) {
        try {
            File file = new File(context.getCacheDir(), UUID.randomUUID().toString());
            OutputStream output = new FileOutputStream(file);
            byte[] buffer = new byte[4 * 1024];
            int read;

            while ((read = input.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }

            output.flush();
            output.close();
            input.close();

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    static public boolean installApk(
        String path
    )
        throws Exception
    {
        if (Shell.SU.available()) {
            if(Shell.SU.run("pm install -r " + path) == null) {
                throw new Exception("Error executing pm install.");
            } else {
                return true;
            }
        } else {
            throw new Exception("Root not available.");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////