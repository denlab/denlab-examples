 {$args,$template,$templatemap}
| .args
| to_entries
| reduce .[]  as {$key,$value}
  (
    $template
    ;
    $templatemap[$key] as $paths
    | reduce $paths[] as $path
      (
        $template ; setpath($path ; $value)
      )
  )
