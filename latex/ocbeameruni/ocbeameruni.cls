\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{ocbeameruni}[2017/09/20 OC presentation class using the university logo]


%%%
% IMPORTANT: Please use XeLaTeX for compilation (for now)!
%%%


%%% Options processing


% Whether to show the outline at the start of every subsection.
\DeclareOption{manyoutlines}{%
  \AtEndOfClass{%
    \AtBeginSubsection[]
    {
      \begin{frame}<beamer>{Gliederung}
        \tableofcontents[currentsection,currentsubsection]
      \end{frame}
    }
  }
}

% Whether to enable black/white mode.
\DeclareOption{blackwhite}{%
  \AtEndOfClass{%
    \setbeamercolor{palette primary}{bg=white,fg=black}
    \setbeamercolor{palette primary}{bg=white,fg=black}
    \setbeamercolor{progress bar}{fg=black}
    \setbeamercolor{alerted text}{fg=black}
    \setbeamercolor{background canvas}{bg=white}
  }
}

% Just pass all other options to beamer
\DeclareOption*{%
  \PassOptionsToClass{\CurrentOption}{beamer}%
}

% "To process the options with which the file was called, you should use:"
\ProcessOptions\relax


%%% Beamer setup


% Inherit from beamer.
\LoadClass{beamer}

% Use metropolis theme.
\usetheme[%
  block=fill,
]{metropolis}
\setbeamertemplate{caption}[default]


%%% Colours


\definecolor{OCGreen}{HTML}{4ca83d} % chair logo colour (from svg logo)
% \definecolor{OCGreen}{HTML}{489427} % chair logo colour (from png logo)
% \definecolor{OCLogoDark}{HTML}{145027} % official OC logo, dark part (“computing”)
% \definecolor{OCLogoLight}{HTML}{96b33c} % official OC logo, light part (“organic”)
\definecolor{green}{HTML}{4ca83d} % chair logo colour (from svg logo)

% Colours are mainly based on the chair logo colour.
\setbeamercolor{palette primary}{bg=OCGreen,fg=white}
\setbeamercolor{progress bar}{fg=OCGreen}
\setbeamercolor{alerted text}{fg=OCGreen}

% alerted text additionally also be bold
\setbeamerfont{alerted text}{series=\bfseries}


%%% Logo


\RequirePackage{textpos}
\RequirePackage{calc}
% \textblockcolour{background canvas.bg}
% \textblockcolour{palette primary.bg}
% TODO bad fix b/c hard coded… however it doesn't work after plain slides otherwise
\textblockcolour{black!2}
\addtobeamertemplate{frametitle}{}{%
  \begin{textblock*}{100mm}(\textwidth-1.5cm,-1.1cm)
    \includegraphics[height=1.11cm]{Logo.pdf}
  \end{textblock*}
}
% \makeatletter
% \newlength\beamerrightmargin
% \setlength\beamerrightmargin{\Gm@rmargin}
% \makeatother
% \addtobeamertemplate{frametitle}{}{%
%   \begin{textblock*}{2.5cm}[0,1](\textwidth-\beamerrightmargin-\marginparwidth-\marginparsep,0cm)
%     % \includegraphics[width=2.5cm]{Logo.pdf}
%     \includegraphics[height=\topmargin]{Logo.pdf}
%   \end{textblock*}
% }


%%% Font setup


\setmonofont[StylisticSet={1,3},Scale=MatchUppercase,BoldFont=Inconsolata-Bold.ttf]{Inconsolata-Regular.ttf}


%%% Misc packages


% Instead of Babel.
\RequirePackage[babelshorthands]{polyglossia}
\setdefaultlanguage{english}

\RequirePackage{csquotes}

\RequirePackage{caption}
\captionsetup[figure]{labelformat=empty}

\RequirePackage{realboxes}


%%% Bibliography


% fonts for the bibliography
\setbeamerfont{bibliography entry author}{size=\footnotesize,%
                                          series=\normalfont}
\setbeamerfont{bibliography entry title}{size=\footnotesize,%
                                         series=\bfseries}
\setbeamerfont{bibliography entry location}{size=\footnotesize,%
                                            series=\normalfont}
\setbeamerfont{bibliography entry note}{size=\footnotesize,%
                                        series=\normalfont}

% numeric makes the symbol move closer to the actual entry
\RequirePackage[backend=biber,style=numeric]{biblatex}
