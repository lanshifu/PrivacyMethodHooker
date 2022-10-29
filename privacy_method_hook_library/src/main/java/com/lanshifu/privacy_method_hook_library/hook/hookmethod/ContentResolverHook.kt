package com.lanshifu.privacy_method_hook_library.hook.hookmethod

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.privacy_method_hook_library.hook.checkCacheAndPrivacy
import com.lanshifu.privacy_method_hook_library.hook.savePrivacyMethodResult

/**
 * @author lanxiaobin
 * @date 2022/9/27
 */
@Keep
object ContentResolverHook {

    @JvmStatic
    @AsmMethodReplace(
        oriClass = ContentResolver::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun query(
        contentResolver: ContentResolver,
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?,
        callerClassName: String
    ): Cursor? {

        /// 只处理特殊，其它放行
        if (ifNotHook(uri.toString())) {
            return contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)
        }

        val key = "ContentResolver#query"
        val checkResult = checkCacheAndPrivacy<Cursor>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }


        return try {
            savePrivacyMethodResult(
                key,
                contentResolver.query(uri, projection, selection, selectionArgs, sortOrder),
                callerClassName
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Uri uri,
    @Nullable String[] projection, @Nullable Bundle queryArgs,
    @Nullable CancellationSignal cancellationSignal
     */
    @RequiresApi(Build.VERSION_CODES.O)
    @JvmStatic
    @AsmMethodReplace(
        oriClass = ContentResolver::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun query(
        contentResolver: ContentResolver,
        uri: Uri,
        projection: Array<String>?,
        queryArgs: Bundle?,
        cancellationSignal: CancellationSignal?,
        callerClassName: String
    ): Cursor? {

        /// 只处理特殊，其它放行
        if (ifNotHook(uri.toString())) {
            return contentResolver.query(uri, projection, queryArgs, cancellationSignal)
        }

        val key = "ContentResolver#query"
        val checkResult = checkCacheAndPrivacy<Cursor>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }


        return try {
            savePrivacyMethodResult(
                key,
                contentResolver.query(uri, projection, queryArgs, cancellationSignal),
                callerClassName
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Uri uri,
    @Nullable String[] projection, @Nullable String selection,
    @Nullable String[] selectionArgs, @Nullable String sortOrder,
    @Nullable CancellationSignal cancellationSignal
     */
    @RequiresApi(Build.VERSION_CODES.O)
    @JvmStatic
    @AsmMethodReplace(
        oriClass = ContentResolver::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun query(
        contentResolver: ContentResolver,
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?,
        cancellationSignal: CancellationSignal?,
        callerClassName: String
    ): Cursor? {

        /// 只处理特殊，其它放行
        if (ifNotHook(uri.toString())) {
            return contentResolver.query(
                uri,
                projection,
                selection,
                selectionArgs,
                sortOrder,
                cancellationSignal
            )
        }

        val key = "ContentResolver#query"
        val checkResult = checkCacheAndPrivacy<Cursor>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }


        return try {
            savePrivacyMethodResult(
                key,
                contentResolver.query(
                    uri,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder,
                    cancellationSignal
                ),
                callerClassName
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    private fun ifNotHook(uri: String): Boolean {
        if (uri != "content://telephony/siminfo") {
            return true
        }
        return false
    }

}