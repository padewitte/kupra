#!/bin/sh
# Mongo REST Camel launcher
_debug () {
	if [ -n "${DEBUG}" ]
	then
		echo "DEBUG:   $1" >&2
	fi
}

JAVACMD=$(which java)
if [ -n "${JAVACMD}" ] && [ -x "${JAVACMD}" ]
then
	_debug "Using \$PATH to find java virtual machine."
elif [ -x /usr/bin/java ]
then
	_debug "Using /usr/bin/java to find java virtual machine."
	JAVACMD=/usr/bin/java
fi

MRC_VERSION="0.0.3-SNAPSHOT"

"${JAVACMD}" -jar mrc-${MRC_VERSION}.jar "$@"
