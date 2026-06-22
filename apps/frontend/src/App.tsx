import { AppSidebar } from "@/components/app-sidebar";
import { Button } from "@/components/ui/button";
import { Separator } from "@/components/ui/separator";
import {
  SidebarInset,
  SidebarProvider,
  SidebarTrigger,
} from "@/components/ui/sidebar";

function App() {
  return (
    <SidebarProvider>
      <AppSidebar />
      <SidebarInset>
        <header className="flex h-14 shrink-0 items-center gap-2 border-b px-4">
          <SidebarTrigger className="-ml-1" />
          <Separator orientation="vertical" className="mr-1 h-5" />
          <h1 className="text-sm font-semibold tracking-tight">Overview</h1>
          <div className="ml-auto flex items-center gap-2">
            <Button size="sm" variant="outline">
              Move money
            </Button>
            <Button size="sm">Deposit</Button>
          </div>
        </header>

        <main className="flex flex-1 flex-col gap-4 p-4">
          <div className="grid auto-rows-min gap-4 md:grid-cols-3">
            <div className="aspect-video rounded-xl border bg-muted/40" />
            <div className="aspect-video rounded-xl border bg-muted/40" />
            <div className="aspect-video rounded-xl border bg-muted/40" />
          </div>
          <div className="min-h-[50vh] flex-1 rounded-xl border bg-muted/40" />
        </main>
      </SidebarInset>
    </SidebarProvider>
  );
}

export default App;
