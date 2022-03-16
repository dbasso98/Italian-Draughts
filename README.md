[![Travis CI][travis-shield]][travis-url]
# Italian-Draughts

<div id="top"></div>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

<!-- ABOUT THE PROJECT -->
## About The Project

This repository consists of a Test Driven Development implementation of Italian Draughts using Java 17.
Furthermore, continuous integration was used by means of Travis CI.

### Built With
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![TravisCI](https://img.shields.io/badge/travis%20ci-%232B2F33.svg?style=for-the-badge&logo=travis&logoColor=white)
<p align="right">(<a href="#top">back to top</a>)</p>

### Game Setup
Italian Draughts is a 2 player game on a board of 64 squares, half are white and half are black.
Each player controls 12 pieces of a specific color (black or white) and the board is placed in
such a way so that the rightmost square on both sides is black. Furthermore, all the pieces are
placed in black tiles only.

Shown below, is a typical setup:

<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/e/e0/Damiera.JPG/1920px-Damiera.JPG" width="300">

### Game Play
White always moves first, alternating turns. Each piece (man) can only move diagonally forward of one square.
This implies, that at all times, all the pieces MUST be in a black square.

If a man reaches the opposite side of the board, it is upgraded to a king.
Kings can move forward or backward (always diagonally of one square).

Furthermore, capturing is mandatory. In other words, if a man finds a piece of opposing color in his diagonal
(forward of one square) with a free space in the subsequent diagonal, it must occupy the free position and capture
the opposing piece.
Men can only capture diagonally forward for a maximum of three pieces while kings can capture backwards as well (same
rules apply). Furthermore, kings can only be captured by other kings.

The following criteria apply when capturing:

- A player must always capture the greatest amount of pieces (to a maximum of three). 
- If a player may capture the same amount of pieces with either a man or a king, they
  must do so with the king.
- If the player may capture the same amount of pieces with a king, they must choose
  the capture with most kings.
- If a player may capture the same amount of kings, they must perform the capture with
  a king closest to its proximity.
- If none of these rules apply, the player may choose the best capture according to their
  strategic needs.

### Game Ending
The game ends when one of the following conditions is verified:

- one player has captured all the opposing pieces
- one player resigns
- one player cannot perform any more moves.

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- GETTING STARTED -->
## Getting Started

Clone the repository to a directory. Once in the directory, run:

```java -jar ./build/libs/Italian-Draughts-1.0-SNAPSHOT.jar```

### Prerequisites

Java 17

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- CONTRIBUTING -->
## Contributing

This repository was created with the intention of following Agile Software Development Practices. In particular,
continuous integration was a main factor for this project.
We followed a Test Driven Development approach by continuously refactoring and integrating new features in small steps.

Furthermore, we set up two/three weekly meetings to align and decide future steps. 

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- CONTACT -->
## Contact

Michele Berti

Davide Basso

Andres Bermeo Marinelli

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[travis-url]: https://app.travis-ci.com/dbasso98/Italian-Draughts
[travis-shield]: https://app.travis-ci.com/dbasso98/Italian-Draughts.svg?branch=main
[contributors-shield]: https://img.shields.io/github/contributors/dbasso98/Italian-Draughts.svg?style=for-the-badge
[contributors-url]: https://github.com/dbasso98/Italian-Draughts/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/dbasso98/Italian-Draughts.svg?style=for-the-badge
[forks-url]: https://github.com/dbasso98/Italian-Draughts/network/members
[stars-shield]: https://img.shields.io/github/stars/dbasso98/Italian-Draughts.svg?style=for-the-badge
[stars-url]: https://github.com/dbasso98/Italian-Draughts/stargazers
[issues-shield]: https://img.shields.io/github/issues/dbasso98/Italian-Draughts.svg?style=for-the-badge
[issues-url]: https://github.com/dbasso98/Italian-Draughts/issues
[license-shield]: https://img.shields.io/github/license/dbasso98/Italian-Draughts.svg?style=for-the-badge
[license-url]: https://github.com/dbasso98/Italian-Draughts/blob/main/LICENSE

