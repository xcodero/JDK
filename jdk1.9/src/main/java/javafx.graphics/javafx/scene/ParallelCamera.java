/*
 * Copyright (c) 2010, 2016, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package javafx.scene;

import com.sun.javafx.geom.PickRay;
import com.sun.javafx.geom.Vec3d;
import com.sun.javafx.geom.transform.Affine3D;
import com.sun.javafx.geom.transform.GeneralTransform3D;
import com.sun.javafx.scene.ParallelCameraHelper;
import com.sun.javafx.sg.prism.NGNode;
import com.sun.javafx.sg.prism.NGParallelCamera;

/**
 * Specifies a parallel camera for rendering a scene without perspective correction.
 *
 * <p>If a scene contains only 2D transforms, then the following details are not
 * relevant.
 * This camera defines a viewing volume for a parallel (orthographic) projection;
 * a rectangular box. This camera is always located at center of the window and
 * looks along the positive z-axis. The coordinate system defined by this camera
 * has its origin in the upper left corner of the panel with the Y-axis pointing
 * down and the Z axis pointing away from the viewer (into the screen). The
 * units are in pixel coordinates.
 *
 * @since JavaFX 2.0
 */
public class ParallelCamera extends Camera {
    static {
        ParallelCameraHelper.setParallelCameraAccessor(new ParallelCameraHelper.ParallelCameraAccessor() {
            @Override
            public NGNode doCreatePeer(Node node) {
                return ((ParallelCamera) node).doCreatePeer();
            }
        });
    }

    {
        // To initialize the class helper at the begining each constructor of this class
        ParallelCameraHelper.initHelper(this);
    }

    /**
     * Creates an empty instance of ParallelCamera.
     */
    public ParallelCamera() {
    }

    @Override
    Camera copy() {
        ParallelCamera c = new ParallelCamera();
        c.setNearClip(getNearClip());
        c.setFarClip(getFarClip());
        return c;
    }

    /*
     * Note: This method MUST only be called via its accessor method.
     */
    private NGNode doCreatePeer() {
        final NGParallelCamera peer = new NGParallelCamera();
        peer.setNearClip((float) getNearClip());
        peer.setFarClip((float) getFarClip());
        return peer;
    }

    @Override
    final PickRay computePickRay(double x, double y, PickRay pickRay) {
        return PickRay.computeParallelPickRay(x, y, getViewHeight(),
                getCameraTransform(),
                getNearClip(), getFarClip(), pickRay);
    }

    @Override
    void computeProjectionTransform(GeneralTransform3D proj) {
        final double viewWidth = getViewWidth();
        final double viewHeight = getViewHeight();
        final double halfDepth =
                (viewWidth > viewHeight) ? viewWidth / 2.0 : viewHeight / 2.0;

        proj.ortho(0.0, viewWidth, viewHeight, 0.0, -halfDepth, halfDepth);
    }

    @Override
    void computeViewTransform(Affine3D view) {
        view.setToIdentity();
    }

    @Override
    Vec3d computePosition(Vec3d position) {
        if (position == null) {
            position = new Vec3d();
        }

        // This is the same math as in PerspectiveCamera, fixed for the default
        // 30 degrees vertical field of view.
        final double halfViewWidth = getViewWidth() / 2.0;
        final double halfViewHeight = getViewHeight() / 2.0;
        final double distanceZ = halfViewHeight / Math.tan(Math.toRadians(15.0));
        position.set(halfViewWidth, halfViewHeight, -distanceZ);

        return position;
    }
}
