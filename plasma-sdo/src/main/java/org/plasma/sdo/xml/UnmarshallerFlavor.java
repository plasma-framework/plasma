package org.plasma.sdo.xml;

/**
 * Indicates a category or type of unmarshaller.
 */
public enum UnmarshallerFlavor {
    /**
     * Simple API for XML unmarshaller
     */
    SAX,
    /**
     * Document Object Model unmarshaller
     */
    DOM,
    /**
     * Streaming API for XML unmarshaller
     */
    STAX
}
