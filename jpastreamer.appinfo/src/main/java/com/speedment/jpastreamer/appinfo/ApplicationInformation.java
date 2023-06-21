/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2022, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.appinfo;

public interface ApplicationInformation {

    /**
     * Returns the vendor of the product. This is primarily used to
     * show meaningful information to the user.
     *
     * @return the vendor
     */
    String vendor();

    /**
     * Returns the title of the product. This is primarily used to
     * show meaningful information to the user.
     *
     * @return the title
     */
    String title();

    /**
     * Returns the subtitle of the product. This is primarily used to
     * show meaningful information to the user.
     *
     * @return the subtitle
     */
    String subtitle();

    /**
     * The name of the official repository of the product. This is
     * used to lookup the latest version.
     *
     * @return  the name of the official repository
     */
    String repository();

    /**
     * Return the non-null version of the applcation implementation. It consists
     * of any string assigned by the vendor of this implementation and does not
     * have any particular syntax specified or expected by the Java runtime. It
     * may be compared for equality with other package version strings used for
     * this implementation by this vendor for this package.
     *
     * @return the non-null version of this application implementation
     */
    String implementationVersion();

    /**
     * Returns the non-null version number of the specification that this
     * application implements. This version string must be a sequence of
     * non-negative decimal integers separated by "."'s and may have leading
     * zeros. When version strings are compared the most significant numbers are
     * compared.
     *
     * @return the non-null version number of the specification that this
     * application implements
     */
    String specificationVersion();

    /**
     * Returns if this version is intended for production use.
     *
     * @return if this version is intended for production use
     */
    default boolean isProductionMode() {
        return !implementationVersion().toUpperCase().contains("EA")
                && !implementationVersion().toUpperCase().contains("SNAPSHOT");
    }

    /**
     * Returns the license name for this product.
     *
     * @return the license name for this product
     */
    String licenseName();

    // Tip: http://patorjk.com/software/taag/#p=display&f=Big&t=Type%20Something%20

    /**
     * Returns a banner that can be printed upon startup.
     *
     * @return a banner that can be printed upon startup
     */
    String banner();

}
