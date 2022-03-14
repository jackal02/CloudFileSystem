# CloudFileSystem
The project emulates an OS Unix based terminal-like File System stored on a Cloud Platform Service.
The files stored in the cloud are maintained in a tree-like structure, file parts being distributed between virtual servers.

# Accepted commands
The accepted commands that can be given to the program are the following:
- newuser <username> <password> <firstname> <lastname> 
- login <username> <password>
- logout
- userinfo
- ls, cd, pwd
- cat, echo
- touch, mkdir, rm

The cloud commands are the following:
- listcloud (lists the files stored in the cloud)
- upload <dirname> (uploads a directory to the cloud recursively)
- sync <dirname> (syncs the current content of a directory in the cloud)
