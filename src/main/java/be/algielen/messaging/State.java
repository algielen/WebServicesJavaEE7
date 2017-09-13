package be.algielen.messaging;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum
public enum State {
    WAITING, STARTING, PROCESSING, FAILURE, DONE
}
