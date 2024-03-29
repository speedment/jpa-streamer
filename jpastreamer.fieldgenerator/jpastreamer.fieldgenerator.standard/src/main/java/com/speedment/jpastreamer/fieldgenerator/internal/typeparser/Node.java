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
package com.speedment.jpastreamer.fieldgenerator.internal.typeparser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A simple class to represent a type as a node where the type parameters
 * correspond to node children. A node without children does not have parameters.
 *
 * @author juliagustafsson
 * @since 0.1.0
 */
public final class Node {

    private final Type type;
    private List<Node> children;

    Node(Type type) {
        this.type = type;
        children = new ArrayList<>();
    }

    Node(Type type, Node... children) {
        this.type = type;
        this.children = new ArrayList<>();
        this.children.addAll(Arrays.asList(children));
    }

    void addChild(Node child) {
        children.add(child);
    }

    Type type() {
        return type;
    }

    List<Node> children() {
        return children;
    }

    boolean isLeaf() {
        return children.isEmpty();
    }

    public @Override
    String toString() {
        return type.getTypeName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return type.equals(node.type) &&
                children.equals(node.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, children);
    }
}
