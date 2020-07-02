Stream.of(                           // Creates a Stream with the given content
        "Zlatan",
        "Tim",
        "Bo",
        "George",
        "Adam",
        "Oscar"
        )
        .filter(n -> n.length() > 2)
        // Retains only those Strings that are longer than 2 characters (i.e. "Bo" is dropped)
        .sorted()
        // Sorts the remaining Strings in natural order
        .collect(Collectors.toList());
        // Collects the remaining sorted Strings in a List