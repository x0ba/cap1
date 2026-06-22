export function OverviewPage() {
  return (
    <div>
      <div className="grid auto-rows-min gap-4 md:grid-cols-3">
        <div className="aspect-video rounded-xl border bg-muted/40" />
        <div className="aspect-video rounded-xl border bg-muted/40" />
        <div className="aspect-video rounded-xl border bg-muted/40" />
      </div>
      <div className="min-h-[50vh] flex-1 rounded-xl border bg-muted/40" />
    </div>
  );
}
