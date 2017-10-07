#!/bin/bash
################################################################################
#
# Tracecompass Libraries Downloader with Maven installer support
#
################################################################################

usage () {
    {
        echo
        echo "Usage: $0 [options]"
        echo
        echo "Options:"
        echo "  -t   list tracked libraries jar files"
        echo "  -d   list and download tracked libraries jar files"
        echo "  -u   removes from maven local repository all the libraries installed through this tool (removes folder mytracecompass)"
        echo "  -i   list and install in maven local repository tracked libraries jar files (see folder mytracecompass), with a constant 1.0.0 version number"
        echo "  -l   list all available plugin jar files (overrides -d/-l flags)"
        echo
    } >&2
    exit 1
}

opt_cmd=
while getopts "tldiu" option; do
    case "$option" in
        l)  opt_cmd="list_all";;
        t)  opt_cmd="list";;
        d)  opt_cmd="download";;
	i)  opt_cmd="install";;
	u)  opt_cmd="uninstall";;
        *)  usage;;
    esac
done

[ -z $opt_cmd ] && usage

################################################################################

# Array of jar-files to track (name until underscore before version)
plugins_tracked=(
    # org.eclipse.tracecompass.tmf.ctf.core \
    # org.eclipse.tracecompass.ctf.parser.source \
    org.eclipse.tracecompass.ctf.parser \
    org.eclipse.tracecompass.ctf.core \
    org.eclipse.tracecompass.common.core \
)

echo -e "\n Downloading libraries list from Eclipse tracecompass stable repo...  (might take some time)"

url="http://download.eclipse.org/tracecompass/stable/repository/plugins/"
list=$(curl -s $url | sed -rn 's/^<img.*<a href=\x27(.*)\x27> .*/\1/p' | grep ".jar$")

printf "\n %-65s %-15s %s\n" "Plugin" "Version" "Date"
for n in $(seq 1 96); do echo -n "-"; done; echo

while read -r line; do
    # Vector containing: [0] file name (withouth .jar extenstion), [1] version nr ,[2] build time
    jar_line=($(echo "$line" | sed -rn 's/.*\/(.+)_(.+)\.v*(.+)\.jar.*/\1  \2  \3/p'))
    # File to be installed with .jar extension
    jar_file=($(echo "$line" | sed -n 's/.*\(org\.*\)/\1/p'))
    # groupId and artifactID parsed from line
    group_art_id=($(echo "${jar_line[0]}" | sed -rn 's/(.+)\.(.+)/\1  \2/p'))
    # groupId changes to with "mytracecompass" to install in our own folder in maven local repo
    my_group_id=($(echo "${group_art_id[0]}" | sed 's/tracecompass/mytracecompass/'))
    # source to get JavaDoc
    line_doc=($(echo "${line}" | sed 's/_/.\source_/'))

case "$opt_cmd" in
    list_all)
        printf " %-65s %-15s %s\n" ${jar_line[0]} ${jar_line[1]} ${jar_line[2]}
	;;
    uninstall)
	echo -e "\n Uninstalling the tracecompass dependencies from local mvn repo."
	rm -rf ~/.m2/repository/org/eclipse/mytracecompass
	break;
	;;
    *)
        for jar in ${plugins_tracked[*]}
        do
            if [[ ${jar_line[0]} == $jar ]]; then
                printf " %-65s %-15s %s\n" ${jar_line[0]} ${jar_line[1]} ${jar_line[2]};
                if [[ $opt_cmd == "download" ]]; then
                    echo
		    mkdir -p sources
		    cd sources
                    wget "http://download.eclipse.org$line"
		    wget "http://download.eclipse.org$line_doc"
		    cd ..
                fi
		if [[ $opt_cmd == "install" ]]; then
                    echo
		    # download jar files
                    wget "http://download.eclipse.org$line"
		    # install jar files in local maven repo under "mytracecompass" folder
		    #
		    # mvn install:install-file -Dfile=${jar_file} -DgroupId=${my_group_id} -DartifactId=${group_art_id[1]} -Dversion=${jar_line[1]} -Dpackaging=jar
		    mvn install:install-file -Dfile=${jar_file} -DgroupId=${my_group_id} -DartifactId=${group_art_id[1]} -Dversion=1.0.0 -Dpackaging=jar
		    # remove the previously downloaded jar file
		    rm ${jar_file}
                fi
                break;
            fi

        done

	;;
esac

done <<< "$list"

echo
