package com.lizhi.smartlife.plugin.privacy_method;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

class PluginUtils implements Opcodes {

  private PluginUtils() {
    throw new AssertionError("no instance");
  }

  static boolean isPrivate(int access) {
    return (access & ACC_PRIVATE) != 0;
  }

  static boolean isPublic(int access) {
    return (access & ACC_PUBLIC) != 0;
  }

  static boolean isStatic(int access) {
    return (access & ACC_STATIC) != 0;
  }

  static boolean isAbstract(int access) {
    return (access & ACC_ABSTRACT) != 0;
  }

  static boolean isbridge(int access) {
    return (access & ACC_BRIDGE) != 0;
  }

  static boolean isSynthetic(int access) {
    return (access & ACC_SYNTHETIC) != 0;
  }

  static boolean isViewOnclickMethod(int access, String name, String desc) {
    return (PluginUtils.isPublic(access) && !PluginUtils.isStatic(access) && !isAbstract(access)) //
        && name.equals("onClick") //
        && desc.equals("(Landroid/view/View;)V");
  }

  static boolean isListViewOnItemOnclickMethod(int access, String name, String desc) {
    return (PluginUtils.isPublic(access) && !PluginUtils.isStatic(access) && !isAbstract(access)) && //
        name.equals("onItemClick") && //
        desc.equals("(Landroid/widget/AdapterView;Landroid/view/View;IJ)V");
  }

  static boolean isBaseQuickAdapterItemOnclickMethod(int access, String name, String desc) {
    // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener.onItemClick
    // void onItemClick(BaseQuickAdapter adapter, View view, int position)
    return (PluginUtils.isPublic(access) && !PluginUtils.isStatic(access) && !isAbstract(access)) && //
        name.equals("onItemClick") && //
        desc.equals("(Lcom/chad/library/adapter/base/BaseViewHolder;Ljava/lang/Object;)V");
  }

  static void addDebouncedAnno(MethodVisitor mv) {
    AnnotationVisitor annotationVisitor =
        mv.visitAnnotation("Lcom/smartdengg/clickdebounce/Debounced;", false);
    annotationVisitor.visitEnd();
  }

  static String convertSignature(String name, String desc) {
    Type method = Type.getType(desc);
    StringBuilder sb = new StringBuilder();
    sb.append(method.getReturnType().getClassName()).append(" ").append(name);
    sb.append("(");
    for (int i = 0; i < method.getArgumentTypes().length; i++) {
      sb.append(method.getArgumentTypes()[i].getClassName());
      if (i != method.getArgumentTypes().length - 1) {
        sb.append(",");
      }
    }
    sb.append(")");
    return sb.toString();
  }
}
