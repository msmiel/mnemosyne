# UNIX Resources

## Tutorials
[Unix for the Beginning Mages](http://unixmages.com/ufbm.pdf)

## Shell Scripting

- [Awesome Bash](https://github.com/awesome-lists/awesome-bash) : Extensive list of bash resources.  

### BASH

*Tutorials* 
- [Learn Enough Command Line to be Dangerous](https://www.learnenough.com/command-line-tutorial)

## Dotfiles 
Please do NOT put anything into your dotfiles without knowing what it does and how it works. 

- [dotfiles](https://dotfiles.github.io/)
- [How to manage dotfiles](https://hackernoon.com/learn-how-to-manage-dotfiles-b8b62c6c5491) 
- [Getting Started with Dotfiles](https://medium.com/@webprolific/getting-started-with-dotfiles-43c3602fd789) 
- [The best way to store your dotfiles](https://developer.atlassian.com/blog/2016/02/best-way-to-store-dotfiles-git-bare-repo/) 

## Brew Commands

The manpage for Brew is [here](https://docs.brew.sh/Manpage). 

*Commands* 
- brew install [package] 
- brew upgrade [package]: upgrades either brew or a specific package 
- brew switch [package] [version]: switches to a specific package version 
- brew list --versions [package]: See what versions of that package installed 

*Package Commands* 
- brew cleanup [package]: remove old versions 
- brew cat [package]: print this formula 
- brew home [package]: opens that package's homepage 

*Global Commands* 
- brew update: bumps minor versions 
- brew list: lists all packages installed 
- brew outdated: checks all of your packages to see if any are out of date 
- brew doctor: use to check if brew is acting strangely 
- brew prune: prunes broken links 

## etc/passwd Output

username:password:UID:GID:UID info:home directory:command/shell


username: the user’s login name
password: the password, will simply be an x if it’s encrypted
user ID (UID): the user’s ID number in the system. 0 is root, 1-99 are for predefined users, and 100-999 are for other system accounts
group ID (GID): Primary group ID, stored in /etc/group.
user ID info: Metadata about the user; phone, email, name, etc.
home directory: Where the user is sent upon login. Generally /home/
command/shell: The absolute path of a command or shell (usually /bin/bash). Does not have to be a shell though!